CREATE DATABASE shopping_online;

CREATE TABLE address(
	idaddress int primary key,
    sonha varchar(100),
    xompho varchar(100),
    lang varchar(100),
    phuongxa varchar(100),
    quanhuyen varchar(100),
    tinhthanh varchar(100)
);

CREATE TABLE role(
	idrole int primary key,
    namerole varchar(100)
);

CREATE TABLE user(
	iduser int primary key,
	username varchar(100),
    password varchar(100),
    fullname varchar(100),
    dob Date,
    email varchar(100),
    phone varchar(12),
    note varchar(100),
    idaddress int,
    FOREIGN KEY (idaddress) REFERENCES address(idaddress)
);

CREATE TABLE product(
	idproduct int primary key,
    productname varchar(100),
    gia double,
    image  longblob,
    solg int,
    daban int,
    note varchar(500),
    iduser int,
    FOREIGN KEY (iduser) REFERENCES user(iduser)
);

CREATE TABLE bill(
	idbill int primary key,
    iduser int,
    idproduct int,
    solg int,
    thanhtien double,
    ngaymua Date,
    FOREIGN KEY (iduser) REFERENCES user(iduser),
    FOREIGN KEY (idproduct) REFERENCES product(idproduct)
);

ALTER TABLE product
MODIFY COLUMN note text;

ALTER TABLE user
ADD COLUMN role varchar(100);

ALTER TABLE bill
ADD COLUMN idaddress int REFERENCES address(idaddress);

INSERT into role(idrole,namerole) values(1,"ROLE_USER");
INSERT into role(idrole,namerole) values(2,"ROLE_ADMIN");

