insert into roles (name) values ('ADMIN');
insert into roles (name) values ('MANAGER');
insert into roles (name) values ('USER');

insert into users (username, email, password, role, telephone, status) values ('john_admin', 'john.admin@example.com', 'admin123', 'ADMIN', '9876543210', true);
insert into users (username, email, password, role, telephone, status) values ('sara_manager', 'sara.manager@example.com', 'manager123', 'MANAGER', '9876543211', true);
insert into users (username, email, password, role, telephone, status) values ('mike_user', 'mike.user@example.com', 'user123', 'USER', '9876543212', true);
