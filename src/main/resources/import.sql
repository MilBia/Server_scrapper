INSERT INTO `role`(`name`, `id`) VALUES ('ADMIN', 1);

INSERT INTO `role`(`name`, `id`) VALUES ('USER', 2);

INSERT INTO `user` (`id`, `username`, `password`, `date_created`, `salt`) VALUES (1,'admin','admin','2020-05-11 22:14:54', '');

INSERT INTO `user` (`id`, `username`, `password`, `date_created`, `salt`) VALUES (2,'user','user','2020-05-11 22:14:54', '');

INSERT INTO `user_roles`(`roles_id`, `user_id`) VALUES (1, 1);

INSERT INTO `user_roles`(`roles_id`, `user_id`) VALUES (2, 2);
