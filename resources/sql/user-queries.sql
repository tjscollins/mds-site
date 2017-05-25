-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, username, pass)
VALUES (:id, :username, :pass)

-- :name update-password! :! :n
-- :doc update an existing user record
UPDATE users
SET pass = :pass
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id

-- :name clear-users! :! :n
-- :doc delete all users from the table.  NOT TO BE USED IN PRODUCTION.
TRUNCATE users;

