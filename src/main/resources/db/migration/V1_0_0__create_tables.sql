CREATE TABLE IF NOT EXISTS `pix_keys`
(
    id binary(16) NOT NULL,
    key_type varchar(9) NOT NULL,
    key_value varchar(77)  NOT NULL,
    account_type varchar(10) NOT NULL,
    account_number int(8) NOT NULL,
    branch_number int(4) NOT NULL,
    name varchar(30) NOT NULL,
    last_name varchar(45),
    created_at datetime(3) NOT NULL,
    updated_at datetime(3) NOT NULL,
    inactivation_date datetime(3),
    active boolean NOT NULL,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX keys_value ON `pix_keys` (key_value);
CREATE INDEX keys_account ON `pix_keys` (account_number, branch_number);