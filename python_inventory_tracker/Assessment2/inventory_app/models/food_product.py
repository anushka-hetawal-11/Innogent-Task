from .product import Product

class FoodProduct(Product):
    def __init__(self, name, stock, price, location, tags, expiry_date):
        super().__init__(name, stock, price, location, tags)
        self.expiry_date = expiry_date

    def describe(self):
        print(f"Name: {self.name} | Stock: {self.stock} | Price: ₹{self.price} | "
              f"Location: {self.location} | Tags: {self.tags} | Expiry: {self.expiry_date}")
