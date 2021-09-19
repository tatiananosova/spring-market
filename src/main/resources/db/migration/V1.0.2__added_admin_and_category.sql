insert into users (username, password, email)
values ('admin', '$2y$10$dBV/tfYTVJkUnOOwCPjFfOyg6FVPAdHzDGEjiYASf.ioiNjNbcr2S', 'admin@gmail.com');

insert into user_role (user_id, role_id)
values  (2, 2),
       (2, 3);

insert into category (name, alias) values ('aaaaaaa', 'a');