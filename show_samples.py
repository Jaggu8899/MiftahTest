import pandas as pd
df = pd.read_excel('Ask_Mimi_1000_Questions_FINAL.xlsx')
categories = df['Category'].unique()
for cat in categories:
    print(f"\n--- {cat} Samples ---")
    samples = df[df['Category'] == cat]['Question'].tail(3).tolist()
    for i, q in enumerate(samples, 1):
        print(f"{i}. {q}")
