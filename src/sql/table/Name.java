package sql.table;

public class Name {
	public static final String Db = "lich";
	public static final String host = "localhost";
	public static final int port = 3306;
	public static final String user = "root";
	public static final String pass = "";
}
/*
 * CREATE TABLE`lich`.`user`(`user` VARCHAR(70) NOT NULL , `pass` VARCHAR(255)
 * NOT NULL , PRIMARY KEY (`user`)) ENGINE = InnoDB; ALTER TABLE thongtin ADD
 * FOREIGN KEY(user) REFERENCES user(user);
 */