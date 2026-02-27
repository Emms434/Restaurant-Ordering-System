INSERT INTO menu_items (name, description, price) VALUES
('Slapping Cheese Sandwich', 'Creamy and cheesy falafel mix', 15.70),
('Vanilla Sunday', 'Creamy vanilla and strawberry mix', 3.05),
('Steak', 'Freshly cooked medium rare steak', 27.50),
('Burger', 'Cheesy beef burger', 14.50)
ON CONFLICT (name) DO NOTHING;
