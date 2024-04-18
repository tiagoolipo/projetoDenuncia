CREATE TABLE tb_users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(80) NOT NULL,
  email VARCHAR(120) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  UNIQUE (email)
);

CREATE TABLE tb_roles (
  role_id INT AUTO_INCREMENT PRIMARY KEY,
  role_name VARCHAR(30) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP
);

CREATE TABLE tb_users_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES tb_users(user_id),
  FOREIGN KEY (role_id) REFERENCES tb_roles(role_id)
);

CREATE TABLE tb_reports (
  report_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  manager_id INT NULL,
  protocol_number VARCHAR(255),
  status VARCHAR(255),
  response TEXT,
  description TEXT NOT NULL,
  date_of_occurrence DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES tb_users(user_id),
  FOREIGN KEY (manager_id) REFERENCES tb_users(user_id)
);

INSERT INTO tb_roles (role_name) VALUES ('USER');
INSERT INTO tb_roles (role_name) VALUES ('ADMIN');