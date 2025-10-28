class Product:
    def __init__(self, name, stock, price, location, tags):
        self.name = name
        self.stock = stock
        self.price = price
        self.location = location
        self.tags = tags

    def value(self):
        return self.stock * self.price

    def describe(self):
        return (f"Name: {self.name} | Stock: {self.stock} | Price: ₹{self.price:.2f} | "
                f"Location: {self.location} | Tags: {self.tags}")