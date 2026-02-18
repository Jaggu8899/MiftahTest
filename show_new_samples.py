import pandas as pd
df = pd.read_excel('My calander.xlsx')
new_cats = ["Upcoming Bookings", "Previous Bookings"]
for cat in new_cats:
    print(f"\n--- {cat} Samples ---")
    samples = df[df['Category'] == cat]['Question'].tail(3).tolist()
    for i, q in enumerate(samples, 1):
        print(f"{i}. {q}")
