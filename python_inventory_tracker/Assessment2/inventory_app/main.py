from services.inventory_service import (
    list_all_products,
    low_stock_warning,
    add_product,
    update_stock,
    delete_product,
    print_total_value,
    apply_discount_by_tag
)

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
        list_all_products()
    elif choice == '2':
        low_stock_warning()
    elif choice == '3':
        add_product()
    elif choice == '4':
        update_stock()
    elif choice == '5':
        delete_product()
    elif choice == '6':
        print_total_value()
    elif choice == '7':
        apply_discount_by_tag()
    elif choice == '8':
        print("Exiting the program")
        break
    else:
        print("Invalid choice. Please try again.")
