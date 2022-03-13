package model.config

case class PostgresConfig(
    driver: String,
    url: String,
    user: String,
    password: String)
