from models.product import Product
from models.food_product import FoodProduct
from services.inventory_service import (
    list_all_products, low_stock_warning, add_product, update_stock,
    delete_product, print_total_value, apply_discount_by_tag
)
from services.stats_service import show_stats_report


inventory = [
    Product("Milk", 10, 30, "shelf-1", {"grocery"}),
    FoodProduct("Chocolate", 3, 50, "shelf-2", {"grocery", "clearance"}, "2025-12-31"),
    Product("Soap", 10, 360, "shelf-2", {"grocery"})
]


def show_menu():
    print("\n Inventory Tracker ")
    print("1. List all products")
    print("2. Low on stock warnings")
    print("3. Add product")
    print("4. Update stock")
    print("5. Delete product")
    print("6. Print total stock value")
    print("7. Apply discount")
    print("8. Show Statistics Report")
    print("9. Exit")


while True:
    show_menu()
    choice = input("Enter your choice (1-9): ")

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
        show_stats_report(inventory)
    elif choice == '9':
        print("Exiting the program.")
        break
    else:
        print("Invalid choice. Please try again.")