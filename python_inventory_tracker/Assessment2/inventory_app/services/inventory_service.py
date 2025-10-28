from models.product import Product
from models.food_product import FoodProduct

LOW_STOCK = 5
inventory = []


def list_all_products():
    print("\nAll Products --")
    if not inventory:
        print("No products found")
    else:
        for product in inventory:
            product.describe()


def low_stock_warning():
    print("\nLow Stock Products --")
    found = False
    for product in inventory:
        if product.stock < LOW_STOCK:
            print(f"{product.name} (Stock: {product.stock})")
            found = True
    if not found:
        print("All items sufficiently stocked.")


def add_product():
    print("\nAdd New Product details --")
    name = input("Enter product name: ")
    stock = int(input("Enter stock quantity: "))
    price = float(input("Enter price: "))
    location = input("Enter location: ")
    tags_input = input("Enter tags separated by commas: ")
    tags = set(tag.strip() for tag in tags_input.split(","))

    is_food = input("Is this a food product? (y/n): ").lower()
    if is_food == 'y':
        expiry = input("Enter expiry date: ")
        product = FoodProduct(name, stock, price, location, tags, expiry)
    else:
        product = Product(name, stock, price, location, tags)

    inventory.append(product)
    print(f"Product '{name}' added successfully!")


def update_stock():
    name = input("Enter the product name to update: ").strip()
    for product in inventory:
        if product.name.lower() == name.lower():
            new_stock = int(input(f"Enter new stock for {name}: "))
            product.stock = new_stock
            print(f"Stock for {name} updated successfully!")
            return
    print(f"Product '{name}' not found in inventory.")


def delete_product():
    name = input("Enter the product name to delete: ").strip()
    for product in inventory:
        if product.name.lower() == name.lower():
            inventory.remove(product)
            print(f"Product '{name}' deleted successfully!")
            return
    print(f"Product '{name}' not found in inventory.")


def print_total_value():
    if not inventory:
        print("No products in inventory.")
        return
    total_value = sum(product.value() for product in inventory)
    print(f"Total value of all products in stock: ₹{total_value:.2f}")


def apply_discount_by_tag():
    tag_to_discount = input("Enter the tag to apply discount (e.g., clearance): ").strip().lower()
    discount_rate = 0.5
    discounted_items = []

    for product in inventory:
        if tag_to_discount in (tag.lower() for tag in product.tags):
            old_price = product.price
            new_price = old_price * (1 - discount_rate)
            product.price = new_price
            discounted_items.append((product.name, old_price, new_price))

    if discounted_items:
        print("\nDiscount Applied to:")
        for name, old, new in discounted_items:
            print(f"{name}: Old Price ₹{old:.2f} → New Price ₹{new:.2f}")
    else:
        print(f"No products found with tag '{tag_to_discount}'.")
