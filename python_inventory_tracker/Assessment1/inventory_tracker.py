inventory = [
    {
        "name": "Milk",
        "stock": 10,
        "price": 30,
        "location": "shelf-1",
        "tags": {"grocery"}
    },
    {
        "name": "Chocolate",
        "stock": 3,
        "price": 50,
        "location": "shelf-2",
        "tags": {"grocery", "clearance"}
    },
    {
        "name": "Soap",
        "stock": 10,
        "price": 360,
        "location": "shelf-2",
        "tags": {"grocery"}
    }
]


def list_all_products(inventory):
    print("\nAll Products --")
    if not inventory:  
        print("No products found")
    else:
        for product in inventory:
            print(f"Name: {product['name']} | Stock: {product['stock']} | Price: {product['price']} | "
                  f"Location: {product['location']} | Tags: {product['tags']}")
  

def low_stock_warning(inventory):
    LOW_STOCK = 5
    print("\nLow Stock Products --")
    found = False  

    for product in inventory:
        if product['stock'] < LOW_STOCK:
            print(f"{product['name']} (Stock: {product['stock']})")
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

    new_product = {
        "name": name,
        "stock": stock,
        "price": price,
        "location": location,
        "tags": tags
    }

    inventory.append(new_product)
    print(f"Product '{name}' added successfully! ")


def update_stock(inventory):
    name = input("Enter the product name to update: ").strip()
    for product in inventory:
        if product["name"].lower() == name.lower():  # case-insensitive match
            new_stock = int(input(f"Enter new stock for {name}: "))
            product["stock"] = new_stock
            print(f" Stock for {name} updated successfully!")
            return
    print(f" Product '{name}' not found in inventory.")


def delete_product(inventory):
    name = input("Enter the product name to delete: ").strip()
    for product in inventory:
        if product["name"].lower() == name.lower():
            inventory.remove(product)
            print(f" Product '{name}' deleted successfully!\n")
            return
    print(f" Product '{name}' not found in inventory.\n")


def print_total_value(inventory):
    if not inventory:
        print("No products in inventory.\n")
        return
    total_value = 0

    for product in inventory:
        product_value = product["stock"] * product["price"]
        total_value += product_value
    print(f" Total value of all products in stock: ₹{total_value:.2f}\n")


def apply_discount_by_tag(inventory):
    tag_to_discount = input("Enter the tag to apply discount (e.g., clearance): ").strip().lower()
    discount_rate = 0.5 
    
    discounted_items = []
    
    for product in inventory:
        if tag_to_discount in (tag.lower() for tag in product["tags"]):
            old_price = product["price"]
            new_price = old_price * (1 - discount_rate)
            product["price"] = new_price
            discounted_items.append((product["name"], old_price, new_price))
    
    if discounted_items:
        print("\n Discount Applied to:")
        for name, old, new in discounted_items:
            print(f"{name}: Old Price ₹{old:.2f} → New Price ₹{new:.2f}")
        print()
    else:
        print(f" No products found with tag '{tag_to_discount}'.\n")



def show_menu():
    print("\n Inventory Tracker ")
    print("1. List all products")
    print("2. Low on stock warnings")
    print("3. Add product")
    print("4. Update stock")
    print("5. Delete product")
    print("6. Print total stock value")
    print("7. Apply discount")
    print("8. Exit")


while True:
    show_menu()
    choice = input("Enter your choice (1-8): ")

    if choice == '1':
        list_all_products(inventory)
    elif choice == '2':
        low_stock_warning(inventory)
    elif choice == '3':
        add_product(inventory)
    elif choice == '4':
        update_stock(inventory)
    elif choice == '5':
        delete_product(inventory)
    elif choice == '6':
        print_total_value(inventory)
    elif choice == '7':
        apply_discount_by_tag(inventory)
    elif choice == '8':
        print("Exiting the program")
        break
    else:
        print("Invalid choice. Please try again.")