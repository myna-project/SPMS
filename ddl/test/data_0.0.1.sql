INSERT INTO users (id, username, name, surname, password, enabled, email, style, lang) VALUES (1, 'admin', 'Admin', 'Admin', 'PBKDF2$sha512$100000$LVNAfWF2GAdHpGD34SbuWg==$kZJDqUF8NoMWDwRbBUduCNz95KT6BfmmScucNKSPYvgGsKauLz5u83J2mBqW/4r98heHczvyvQzS/B/XYY+Chw==', 1, 'admin@admin.it', 'purple', 'it');
INSERT INTO users (id, username, name, surname, password, enabled, email, style, lang) VALUES (2, 'user1', 'Utente', 'Uno', 'PBKDF2$sha512$100000$B8LPtpMevJX/Pk7SQLK2Eg==$qj2j8egtzOCU3ihEVYB2P8d+czUTH8gIGyfSPv1w1GqyTwZXHAcgwi+t/NcGCJlKfNzHmiTmK17//HxBwotnDg==', 1, 'user1@user1.it', 'purple', 'it');
INSERT INTO users (id, username, name, surname, password, enabled, email, style, lang) VALUES (3, 'resp1', 'Responsabile', 'Uno', 'PBKDF2$sha512$100000$HZdN9VhmVhnxaSVzluMzAg==$i71tCwir35Vei/xL4aD8CO66yZ3Yj0ola/DI/V+tnS0pgfRpORoWQmqof2ZotnvtkV1M5wXmfs4PKRRlGV+/9g==', 1, 'resp@resp1.it', 'purple', 'it');

INSERT INTO roles (id, name, description) VALUES (1, 'ROLE_ADMIN', 'I can do everything');
INSERT INTO roles (id, name, description) VALUES (2, 'ROLE_USER', 'I can set production data');
INSERT INTO roles (id, name, description) VALUES (3, 'ROLE_RESP', 'I can set and validate production data');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1), (2, 2), (3, 3);