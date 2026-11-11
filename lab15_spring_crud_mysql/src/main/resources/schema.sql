DROP TABLE IF EXISTS product;

create table product (
 id int not null AUTO_INCREMENT PRIMARY KEY,
 name varchar(100) not null,
 description varchar(100),
 price DECIMAL(8,2)
);
