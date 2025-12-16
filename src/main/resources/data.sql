INSERT INTO country (name, code) VALUES
('Ukraine', 'UKR'),
('Russia', 'RUS'),
('United States', 'USA');


INSERT INTO conflict (name, start_date, status, description) VALUES
('Ukraine War', '2022-02-24', 'ACTIVE',
 'Armed conflict between Ukraine and Russia'),
('Cold War', '1947-03-12', 'ENDED',
 'Period of geopolitical tension between the USA and the Soviet Union');


INSERT INTO conflict_country (conflict_id, country_id) VALUES
(1, 1),
(1, 2),
(2, 3);


INSERT INTO faction (name, conflict_id) VALUES
('Ukraine Forces', 1),
('Russian Forces', 1),
('NATO', 2);


INSERT INTO faction_country (faction_id, country_id) VALUES
(1, 1),
(2, 2),
(3, 3);


INSERT INTO event (event_date, location, description, conflict_id) VALUES
('2022-02-24', 'Kyiv', 'Beginning of the Russian invasion of Ukraine', 1),
('2022-03-01', 'Kharkiv', 'Heavy fighting reported in the city', 1),
('1989-11-09', 'Berlin', 'Fall of the Berlin Wall', 2);
