CREATE SCHEMA IF NOT EXISTS message_container_schema;

CREATE TABLE IF NOT EXISTS message_container_schema.message(
                                                                      id SERIAL PRIMARY KEY,
                                                                      message TEXT NOT NULL,
                                                                      id_user_sender SERIAL NOT NULL,
                                                                      id_user_receiver SERIAL NOT NULL,
                                                                      date_time_message TIMESTAMP NOT NULL
);
