USE Assignment2;

INSERT INTO `User`(`name`, `userType`, `password`) VALUES ('owner', 'owner', 'owner'),
('seller', 'seller', 'seller'), ('cashier', 'cashier', 'cashier');
INSERT INTO `User`(`name`, `userType`, `password`) VALUES ('anon', 'anon', 'anon'),
('test', 'test', 'test'), ('user', 'customer', 'user');

INSERT INTO `Cash`(`price`, `quantity`) VALUES ('50', 5), ('20', 5), ('10', 5), ('5', 5), ('2', 5)
, ('1', 5), ('0.5', 5);
INSERT INTO `Cash`(`price`, `quantity`) VALUES ('test1', 5);

INSERT INTO `Snacks`(`name`, `category`, `quantity`, `price`, `code`, `image`) VALUES
('Mineral Water', 'Drinks', 7, 3.6, 1001, '/fxml/icons/Mineral_Water.jpg'), ('Sprite', 'Drinks', 7, 4.8, 1002, '/fxml/icons/sprite_image.jpg'),
('Coca cola', 'Drinks', 7, 4.8, 1003, '/fxml/icons/coke_image.jpg'), ('Pepsi', 'Drinks', 7, 4.8, 1004, '/fxml/icons/pepsi_image.jpg'),
("Juice", "Drinks", 7, 5, 1005, '/fxml/icons/juice_image.jpg');
INSERT INTO `Snacks`(`name`, `category`, `quantity`, `price`, `code`, `image`) VALUES
("Mars", "Chocolates", 7, 2, 1006, '/fxml/icons/mars_image.jpg'), ("M&M", "Chocolates", 7, 2.5, 1007, '/fxml/icons/m&m_image.jpg'),
("Bounty", "Chocolates", 7, 3, 1008, '/fxml/icons/bounty_image.jpg'), ("Snickers", "Chocolates", 7, 3.5, 1009, '/fxml/icons/snickers_image.jpg');
INSERT INTO `Snacks`(`name`, `category`, `quantity`, `price`, `code`, `image`) VALUES
("Smiths", "Chips", 7, 3, 1010, '/fxml/icons/smiths_image.jpg'), ("Pringles", "Chips", 7, 3, 1011, '/fxml/icons/pringles_image.jpg'),
("Kettle", "Chips", 7, 3, 1012, '/fxml/icons/kettle_image.jpg'), ("Thins", "Chips", 7, 3, 1013, '/fxml/icons/thins_image.jpg');
INSERT INTO `Snacks`(`name`, `category`, `quantity`, `price`, `code`, `image`) VALUES
("Mentos", "Candies", 7, 2, 1014, '/fxml/icons/mentos_image.jpg'), ("Sour Patch", "Candies", 7, 2, 1015, '/fxml/icons/sour_patch_image.jpg'),
("Skittles", "Candies", 7, 2, 1016, '/fxml/icons/skittles_image.jpg');
INSERT INTO `Snacks`(`name`, `category`, `quantity`, `price`, `code`) VALUES
("Test item 1", "Test", 7, 2, 2001), ("Test item 2", "Test", 7, 2, 2002),
("Test item 3", "Test", 7, 2, 2003);
