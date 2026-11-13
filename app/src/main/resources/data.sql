-- Tasks Dummy Data with explicit creation times
INSERT INTO tasks (description, timestamp_created, timestamp_due, complete)
VALUES
    ('Review Q4 financial projections', '2026-10-25 09:00:00+00', '2026-11-10 15:00:00+00', FALSE),
    ('Schedule maintenance for the espresso machine', '2026-10-28 10:15:00+00', '2026-11-10 15:00:00+00', FALSE),
    ('Update website privacy policy', '2026-11-01 14:30:00+00', '2026-11-15 09:30:00+00', FALSE),
    ('Email client about the project delay', '2026-11-05 11:20:00+00', '2026-11-07 14:00:00+00', TRUE),
    ('Order new office supplies and printer ink', '2026-11-06 16:45:00+00', '2026-11-12 11:00:00+00', FALSE);