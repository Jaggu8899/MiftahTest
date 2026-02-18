package Test3;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v144.network.Network;
import org.openqa.selenium.devtools.v144.network.model.Request;
import org.openqa.selenium.devtools.v144.network.model.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.github.bonigarcia.wdm.WebDriverManager;

public class loginTest {

	public static void main(String[] args) throws Exception {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
		ChromeDriver driver = new ChromeDriver(options);

		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty()));

		Map<String, String> reqIdToMethod = new ConcurrentHashMap<>();
		Map<String, String> requestPayloads = new ConcurrentHashMap<>();
		Map<String, Map<String, Object>> requestHeadersMap = new ConcurrentHashMap<>();
		StringBuilder networkLogs = new StringBuilder();
		java.util.concurrent.atomic.AtomicBoolean captured = new java.util.concurrent.atomic.AtomicBoolean(false);

		devTools.addListener(Network.requestWillBeSent(), request -> {
			Request req = request.getRequest();
			String url = req.getUrl();
			if (url.contains("signin-via-password") && "POST".equals(req.getMethod())) {
				String reqId = request.getRequestId().toString();
				reqIdToMethod.put(reqId, req.getMethod());
				requestHeadersMap.put(reqId, req.getHeaders().toJson());
				if (req.getPostData().isPresent()) {
					requestPayloads.put(reqId, req.getPostData().get());
				}
			}
		});

		devTools.addListener(Network.responseReceived(), response -> {
			Response res = response.getResponse();
			String url = res.getUrl();
			String reqId = response.getRequestId().toString();
			String method = reqIdToMethod.getOrDefault(reqId, "UNKNOWN");

			// Specifically target the login endpoint and ensure only one capture
			if (url.contains("signin-via-password") && "POST".equals(method) && captured.compareAndSet(false, true)) {

				networkLogs.append("1) General\n");
				networkLogs.append("Request URL: ").append(url).append("\n");
				networkLogs.append("Request Method: ").append(method).append("\n");
				networkLogs.append("Status Code: ").append(res.getStatus()).append(" ").append(res.getStatusText())
						.append("\n");
				String ip = res.getRemoteIPAddress().isPresent() ? res.getRemoteIPAddress().get() : "N/A";
				String port = res.getRemotePort().isPresent() ? res.getRemotePort().get().toString() : "N/A";
				networkLogs.append("Remote Address: ").append(ip).append(":").append(port).append("\n");
				String refPol = res.getHeaders().toJson().getOrDefault("referrer-policy", "no-referrer-when-downgrade")
						.toString();
				networkLogs.append("Referrer Policy: ").append(refPol).append("\n\n");

				networkLogs.append("2) Response Headers\n");
				res.getHeaders().toJson().forEach((k, v) -> networkLogs.append(k).append(": ").append(v).append("\n"));
				networkLogs.append("\n");

				networkLogs.append("3) Request Headers\n");
				Map<String, Object> reqHeaders = requestHeadersMap.get(reqId);
				if (reqHeaders != null) {
					reqHeaders.forEach((k, v) -> networkLogs.append(k).append(": ").append(v).append("\n"));
				}
				networkLogs.append("\n");

				networkLogs.append("4) Request Payload\n");
				String payload = requestPayloads.get(reqId);
				networkLogs.append(payload != null ? prettyPrintJson(payload) : "(No payload captured)").append("\n\n");

				networkLogs.append("5) Response Body\n");
				try {
					String body = devTools.send(Network.getResponseBody(response.getRequestId())).getBody();
					networkLogs.append(prettyPrintJson(body)).append("\n");
				} catch (Exception e) {
					networkLogs.append("(No response body available)\n");
				}
			}
		});

		try {
			driver.get("https://dev.miftah.ai/login");
			driver.manage().window().maximize();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jagadeeswara89@gmail.com");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Jaggu@89");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[text()='Login']")).click();
			Thread.sleep(5000);
		} finally {
			saveLogs(networkLogs.toString());
			driver.quit();
		}
	}

	private static void saveLogs(String content) {
		try {
			File logDir = new File("src/test/resource/logs");
			if (!logDir.exists())
				logDir.mkdirs();
			File logFile = new File(logDir, "Login.log");
			try (FileWriter fw = new FileWriter(logFile)) {
				fw.write(content);
			}
			System.out.println("Logs successfully saved to: " + logFile.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("Error saving logs: " + e.getMessage());
		}
	}

	private static String prettyPrintJson(String json) {
		try {
			JsonElement jsonElement = JsonParser.parseString(json);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(jsonElement);
		} catch (Exception e) {
			return json;
		}
	}
}
