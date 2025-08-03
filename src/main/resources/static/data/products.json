import pandas as pd
import json
import os

# Read the dataset (tab-separated)
df = pd.read_csv("crop_recommendation.csv", delimiter="\t")
df.columns = df.columns.str.strip()

products = []

# Generate fertilizer products
for fert in df["Fertilizer"].dropna().unique():
    products.append({
        "id": fert.lower().replace(" ", "_"),
        "type": "fertilizer",
        "name": fert,
        "price": 850,  # Default price (you can randomize if needed)
        "image": f"images/fertilizers/{fert.lower().replace(' ', '_')}.jpg",
        "description": f"{fert} is a fertilizer recommended for crop productivity.",
        "available": True
    })

# Generate pesticide products
for pest in df["Pesticide"].dropna().unique():
    # Avoid duplication if fertilizer and pesticide have same name
    if not any(p["name"].lower() == pest.lower() for p in products):
        products.append({
            "id": pest.lower().replace(" ", "_"),
            "type": "pesticide",
            "name": pest,
            "price": 750,
            "image": f"images/pesticides/{pest.lower().replace(' ', '_')}.jpg",
            "description": f"{pest} is a pesticide commonly used for crop protection.",
            "available": True
        })

# Save to products.json
with open("products.json", "w") as f:
    json.dump(products, f, indent=2)

print(f"✅ Successfully created products.json with {len(products)} products!")
