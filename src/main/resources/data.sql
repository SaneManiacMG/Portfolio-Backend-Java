insert into users
(user_id, username, first_name, last_name, email, phone_nr, role, active)
values
('123456789', 'SaneManiacMG', 'Mogomotsi', 'Moroane', 'mmoroane@hotmail.com', '0813916607', 'ADMIN', true),
('456', 'dummy1admin', 'dummy1', 'admin', 'dummy1@email.com', '0123456789', 'ADMIN', true),
('789', 'dummy2user', 'dummy2', 'user', 'dummy2@email.com', '1234567890', 'USER', true),
('101', 'dummy3deactivatedAcc', 'dummy3', 'deactivatedAcc', 'dummy3@email.com', '1234567899', 'USER', false);

insert into logins
(user_id, password)
values
('123456789', '$2a$10$yG4ELtDpzKbpAjDzlT95o.3McaaDOtCbJemFr4No532DOwjzH3d9C');

--password is p@ssw0rd1