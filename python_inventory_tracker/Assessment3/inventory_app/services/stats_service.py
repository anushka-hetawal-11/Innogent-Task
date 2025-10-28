import numpy as np

def show_stats_report(inventory):
    print("\nInventory Statistics Report --")

    if not inventory:
        print("No products found in inventory.\n")
        return

    prices = np.array([product.price for product in inventory])
    stocks = np.array([product.stock for product in inventory])

    avg_price = np.mean(prices)
    max_price = np.max(prices)
    total_stock = np.sum(stocks)

    print("\nTotal Inventory Value per Product:")
    for product in inventory:
        total_value = product.price * product.stock
        print(f"  {product.name}: ₹{total_value:.2f}")

    print(f"\nAverage Price of Items: ₹{avg_price:.2f}")
    print(f"Most Expensive Item Price: ₹{max_price:.2f}")
    print(f"Total Stock Count: {total_stock}")

    tag = input("\nEnter a tag to filter stats (e.g., clearance): ").strip().lower()
    filtered = [p for p in inventory if tag in (t.lower() for t in p.tags)]

    if filtered:
        tag_prices = np.array([p.price for p in filtered])
        tag_values = np.array([p.price * p.stock for p in filtered])
        print(f"\nStats for tag '{tag}':")
        print(f"  Average Price: ₹{np.mean(tag_prices):.2f}")
        print(f"  Total Value: ₹{np.sum(tag_values):.2f}\n")
    else:
        print(f"No products found with tag '{tag}'.\n")