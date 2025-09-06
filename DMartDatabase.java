package dMartData;

public class DMartDatabase {
    public static String[] category = {
        "Groceries", "Fruits & Vegetables", "Dairy & Frozen", "Beverages", "Snacks & Branded Foods",
        "Personal Care", "Home Care", "Stationery & Books", "Baby Products", "Kitchen & Dining",
        "Home & Living", "Apparel", "Footwear", "Electronics & Accessories", "Seasonal & Festive"
    };

    public static String[][] subcategory = {
        {"Staples", "Spices", "Oil & Ghee", "Instant Food", "Dry Fruits"},
        {"Fresh Vegetables", "Fresh Fruits", "Organic"},
        {"Milk & Curd", "Butter & Cheese", "Paneer", "Ice Creams", "Frozen Foods"},
        {"Soft Drinks", "Juices", "Tea", "Coffee", "Health Drinks"},
        {"Biscuits", "Chips", "Namkeen", "Chocolates", "Ready-to-Eat Snacks"},
        {"Soaps", "Shampoo", "Skin Care", "Oral Care", "Sanitary"},
        {"Detergents", "Dishwashing", "Cleaners", "Air Fresheners", "Pest Control"},
        {"Notebooks", "Pens", "Art Supplies", "Diaries", "Stationery Sets"},
        {"Diapers", "Wipes", "Baby Food", "Baby Skin Care", "Baby Bath"},
        {"Cookware", "Cutlery", "Storage", "Utensils", "Kitchen Tools"},
        {"Bedsheets", "Towels", "Curtains", "Cushions", "Storage Solutions"},
        {"Men's Clothing", "Women's Clothing", "Kids' Clothing", "Innerwear", "Winter Wear"},
        {"Casual Shoes", "Sandals", "Flip-Flops", "Formal Shoes", "Slippers"},
        {"Mixers", "Irons", "Extension Boards", "Cables", "Chargers"},
        {"Rakhi Items", "Diwali Gifts", "Fairy Lights", "Decorations", "New Year Specials"}
    };

    public static String[][][] item = {
        { // Groceries
            {"Rice", "Wheat", "Toor Dal", "Chana Dal", "Sugar"},
            {"Turmeric", "Chili Powder", "Coriander", "Jeera", "Garam Masala"},
            {"Sunflower Oil", "Mustard Oil", "Groundnut Oil", "Ghee", "Olive Oil"},
            {"Maggi", "Upma Mix", "Poha", "Soup Mix", "Cup Noodles"},
            {"Almonds", "Cashews", "Raisins", "Walnuts", "Dates"}
        },
        { // Fruits & Vegetables
            {"Tomato", "Potato", "Onion", "Carrot", "Cabbage"},
            {"Apple", "Banana", "Orange", "Grapes", "Papaya"},
            {"Organic Spinach", "Organic Carrot", "Organic Tomato", "Organic Beetroot", "Organic Cucumber"}
        },
        { // Dairy & Frozen
            {"Amul Milk", "Curd", "Slim Milk", "Flavored Milk", "Paneer"},
            {"Amul Butter", "Cheese Slices", "Cheese Cube", "Spread Cheese", "Table Butter"},
            {"Mother Dairy Paneer", "Amul Paneer", "Gowardhan Paneer", "Nestle Paneer", "Fresh Paneer"},
            {"Vanilla", "Chocolate", "Strawberry", "Mango", "Butterscotch"},
            {"Frozen Peas", "French Fries", "Corn", "Cut Mixed Veg", "Ice Cubes"}
        },
        { // Beverages
            {"Coca Cola", "Pepsi", "Thumbs Up", "Sprite", "7Up"},
            {"Tropicana", "Real", "B-Natural", "Slice", "Minute Maid"},
            {"Red Label", "Tata Tea", "Green Tea", "Lipton", "Society Tea"},
            {"Bru", "Nescafe", "Filter Coffee", "Cafe Latte", "Cold Coffee"},
            {"Complan", "Horlicks", "Boost", "Bournvita", "Pediasure"}
        },
        { // Snacks & Branded Foods
            {"Parle-G", "Marie", "Hide & Seek", "Oreo", "Bourbon"},
            {"Lays", "Bingo", "Kurkure", "Uncle Chips", "Too Yumm"},
            {"Haldiram Bhujia", "Navratan Mix", "Moong Dal", "Chana Jor", "Masala Peanuts"},
            {"Dairy Milk", "Perk", "5 Star", "Munch", "KitKat"},
            {"Ready Poha", "Upma Bowl", "Mini Idli", "Dal Chawal", "Rajma Rice"}
        },
        { // Personal Care
            {"Dove", "Lux", "Lifebuoy", "Dettol", "Pears"},
            {"Clinic Plus", "Pantene", "Sunsilk", "Dove Shampoo", "Head & Shoulders"},
            {"Vaseline", "Nivea", "Pond's", "Garnier", "Himalaya"},
            {"Colgate", "Pepsodent", "Sensodyne", "Closeup", "Oral-B"},
            {"Whisper", "Stayfree", "Sofy", "Nua", "Bella"}
        },
        { // Home Care
            {"Surf Excel", "Ariel", "Tide", "Rin", "Nirma"},
            {"Vim Bar", "Pril", "Exo", "Finish", "Harpic"},
            {"Lizol", "Domex", "Phenyl", "Colin", "Sanifresh"},
            {"Odonil", "Ambi Pur", "Godrej Aer", "Room Fresh", "Airwick"},
            {"Hit", "All-Out", "Mortein", "Good Knight", "Baygon"}
        },
        { // Stationery & Books
            {"Classmate Notebook", "Camlin Sketch", "Apsara Pencil", "Natraj Eraser", "Fevistick"},
            {"Reynolds Pen", "Cello Pen", "Uniball Pen", "Parker Pen", "Gel Pen"},
            {"Watercolors", "Crayons", "Color Pencils", "Sketch Pens", "Drawing Book"},
            {"Softcover Diary", "Hardcover Diary", "Pocket Diary", "Planner", "Lock Diary"},
            {"Geometry Set", "Stapler", "Punching Machine", "Sticky Notes", "Clipboard"}
        },
        { // Baby Products
            {"Pampers", "Huggies", "MamyPoko", "Supples", "Little Angel"},
            {"Wet Wipes", "Dry Wipes", "Johnson Wipes", "Mother Sparsh", "Mee Mee"},
            {"Cerelac", "Farex", "Nestum", "Gerber", "Slurrp Farm"},
            {"Baby Lotion", "Baby Oil", "Baby Soap", "Baby Shampoo", "Baby Cream"},
            {"Bubble Bath", "Shower Gel", "Baby Powder", "Baby Bathrobe", "Baby Towel"}
        },
        { // Kitchen & Dining
            {"Nonstick Pan", "Kadhai", "Tawa", "Casserole", "Idli Maker"},
            {"Spoons", "Forks", "Knives", "Ladle", "Tongs"},
            {"Storage Jar", "Tupperware", "Steel Box", "Plastic Container", "Glass Jar"},
            {"Tongs", "Grater", "Peeler", "Whisk", "Chopper"},
            {"Spatula", "Measuring Spoon", "Rolling Pin", "Mortar Pestle", "Strainer"}
        },
        { // Home & Living
            {"Cotton Bedsheet", "Double Bedsheet", "Single Bedsheet", "Pillow Covers", "Mattress Protector"},
            {"Bath Towel", "Hand Towel", "Kitchen Towel", "Face Towel", "Gym Towel"},
            {"Window Curtain", "Door Curtain", "Shower Curtain", "Sheer Curtain", "Blackout Curtain"},
            {"Cushion", "Cushion Cover", "Bean Bag", "Throw", "Bolster"},
            {"Plastic Box", "Cloth Organizer", "Shoe Rack", "Storage Basket", "Hanging Organizer"}
        },
        { // Apparel
            {"T-Shirt", "Shirt", "Jeans", "Shorts", "Jacket"},
            {"Kurti", "Top", "Leggings", "Saree", "Dress"},
            {"Kids T-Shirt", "Kids Jeans", "Frock", "Dungarees", "School Uniform"},
            {"Men Brief", "Men Vest", "Women Bra", "Women Panty", "Socks"},
            {"Sweater", "Jacket", "Gloves", "Woolen Cap", "Thermal"}
        },
        { // Footwear
            {"Sneakers", "Running Shoes", "Casual Shoes", "Formal Shoes", "Boots"},
            {"Slippers", "Floaters", "Sandals", "Clogs", "Slides"},
            {"Flip-Flops", "Crocs", "House Slippers", "Bathroom Slippers", "Beach Flip-Flops"},
            {"Formal Black", "Formal Brown", "Loafers", "Derby", "Oxford"},
            {"Kids Sandals", "Kids Flip-Flops", "Kids Shoes", "Kids Slippers", "Kids Sneakers"}
        },
        { // Electronics & Accessories
            {"Mixer Grinder", "Hand Blender", "Juicer", "Electric Kettle", "Toaster"},
            {"Dry Iron", "Steam Iron", "Garment Steamer", "Ironing Board", "Lint Remover"},
            {"6 Socket Board", "4 Socket Board", "Surge Protector", "Multi Plug", "Smart Plug"},
            {"USB Cable", "HDMI Cable", "LAN Cable", "OTG Cable", "Charging Cable"},
            {"Mobile Charger", "Laptop Charger", "Power Bank", "Car Charger", "Wireless Charger"}
        },
        { // Seasonal & Festive
            {"Rakhi Set", "Designer Rakhi", "Kids Rakhi", "Lumba Rakhi", "Pooja Thali"},
            {"Gift Box", "Dry Fruit Pack", "Sweets Box", "Decor Hamper", "Gift Card"},
            {"Fairy Lights", "LED Curtain", "Rice Lights", "String Lights", "Decor Bulbs"},
            {"Toran", "Wall Hanging", "Diya Set", "Rangoli", "Candles"},
            {"New Year Cap", "Balloons", "Party Horns", "Confetti", "Streamer"}
        }
    };

    public static int[][][] price = {
        {{60, 55, 70, 65, 50}, {40, 42, 45, 43, 48}, {150, 140, 160, 200, 220}, {15, 18, 20, 22, 25}, {500, 520, 530, 550, 600}},
        {{25, 20, 22, 24, 18}, {60, 55, 65, 70, 75}, {35, 37, 40, 38, 36}},
        {{60, 45, 50, 55, 70}, {75, 85, 90, 95, 100}, {110, 120, 115, 125, 130}, {40, 42, 45, 47, 50}, {60, 65, 70, 68, 72}},
        {{30, 32, 35, 28, 29}, {55, 50, 52, 54, 58}, {90, 85, 88, 86, 87}, {95, 100, 110, 105, 115}, {120, 125, 130, 128, 135}},
        {{10, 12, 15, 18, 20}, {20, 22, 25, 28, 30}, {35, 38, 40, 42, 45}, {25, 27, 30, 32, 35}, {50, 52, 55, 57, 60}},
        {{35, 32, 30, 33, 31}, {80, 85, 78, 82, 90}, {110, 115, 120, 125, 130}, {45, 48, 50, 52, 55}, {60, 62, 65, 68, 70}},
        {{90, 85, 88, 82, 80}, {45, 48, 50, 52, 55}, {70, 75, 78, 80, 85}, {60, 62, 65, 68, 70}, {95, 98, 100, 102, 105}},
        {{25, 20, 15, 10, 5}, {10, 12, 15, 18, 20}, {22, 25, 27, 30, 35}, {40, 42, 45, 47, 50}, {55, 57, 60, 62, 65}},
        {{80, 85, 90, 95, 100}, {30, 32, 35, 38, 40}, {120, 125, 130, 135, 140}, {60, 62, 65, 68, 70}, {75, 78, 80, 82, 85}},
        {{200, 220, 240, 260, 280}, {50, 52, 55, 58, 60}, {70, 72, 75, 78, 80}, {40, 42, 45, 48, 50}, {60, 62, 65, 68, 70}},
        {{300, 320, 340, 360, 380}, {90, 95, 100, 105, 110}, {200, 220, 240, 260, 280}, {150, 160, 170, 180, 190}, {100, 110, 120, 130, 140}},
        {{350, 360, 370, 380, 390}, {250, 260, 270, 280, 290}, {150, 160, 170, 180, 190}, {80, 85, 90, 95, 100}, {300, 320, 340, 360, 380}},
        {{500, 520, 540, 560, 580}, {150, 160, 170, 180, 190}, {100, 110, 120, 130, 140}, {200, 220, 240, 260, 280}, {130, 140, 150, 160, 170}},
        {{1500, 1600, 1700, 1800, 1900}, {1000, 1100, 1200, 1300, 1400}, {300, 350, 400, 450, 500}, {100, 120, 140, 160, 180}, {500, 550, 600, 650, 700}},
        {{50, 55, 60, 65, 70}, {300, 320, 340, 360, 380}, {120, 130, 140, 150, 160}, {90, 100, 110, 120, 130}, {60, 70, 80, 90, 100}}
    };
}
