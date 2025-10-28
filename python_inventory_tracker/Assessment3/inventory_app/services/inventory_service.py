from models.product import Product
from models.food_product import FoodProduct

def list_all_products(inventory):
    print("\nAll Products --")
    if not inventory:
        print("No products found")
    else:
        for product in inventory:
            print(product.describe())


def low_stock_warning(inventory):
    LOW_STOCK = 5
    print("\nLow Stock Products --")
    found = False

    for product in inventory:
        if product.stock < LOW_STOCK:
            print(f"{product.name} (Stock: {product.stock})")
            found = True

    if not found:
        print("All items sufficiently stocked.")


def add_product(inventory):
    print("\nAdd New Product details --")
    name = input("Enter product name: ")
    stock = int(input("Enter stock quantity: "))
    price = float(input("Enter price: "))
    location = input("Enter location: ")
    tags_input = input("Enter tags separated by commas: ")
    tags = set(tag.strip() for tag in tags_input.split(","))
    is_food = input("Is this a food product? (y/n): ").lower()

    if is_food == 'y':
        expiry = input("Enter expiry date (e.g., 2025-12-31): ")
        new_product = FoodProduct(name, stock, price, location, tags, expiry)
    else:
        new_product = Product(name, stock, price, location, tags)

    inventory.append(new_product)
    print(f"Product '{name}' added successfully!")


def update_stock(inventory):
    name = input("Enter the product name to update: ").strip()
    for product in inventory:
        if product.name.lower() == name.lower():
            new_stock = int(input(f"Enter new stock for {name}: "))
            product.stock = new_stock
            print(f"Stock for {name} updated successfully!")
            return
    print(f"Product '{name}' not found in inventory.")


def delete_product(inventory):
    name = input("Enter the product name to delete: ").strip()
    for product in inventory:
        if product.name.lower() == name.lower():
            inventory.remove(product)
            print(f"Product '{name}' deleted successfully!\n")
            return
    print(f"Product '{name}' not found in inventory.\n")


def print_total_value(inventory):
    if not inventory:
        print("No products in inventory.\n")
        return

    total_value = sum(p.value() for p in inventory)
    print(f"Total value of all products in stock: ₹{total_value:.2f}\n")


def apply_discount_by_tag(inventory):
    tag_to_discount = input("Enter the tag to apply discount (e.g., clearance): ").strip().lower()
    discount_rate = 0.5
    discounted_items = []

    for product in inventory:
        if tag_to_discount in (tag.lower() for tag in product.tags):
            old_price = product.price
            new_price = old_price * (1 - discount_rate)