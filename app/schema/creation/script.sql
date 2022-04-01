create table block
(
    number             bigint not null
        constraint block_pk
            primary key,
    hash               text   not null,
    parent_hash        text,
    nonce              text,
    sha_3_uncles      text,
    logs_bloom        text,
    transactions_root text,
    state_root        text,
    receipts_root     text,
    author             text,
    miner              text,
    mix_hash          text,
    difficulty         text,
    total_difficulty  text,
    extra_data        text,
    size_               bigint,
    gas_limit         bigint,
    gas_used          bigint,
    time_stamp          timestamp
);

alter table block
    owner to postgres;

create unique index block_hash_uindex
    on block (hash);

create unique index block_number_uindex
    on block (number);

create table transaction
(
    hash               text   not null,
    nonce              text,
    transaction_index text,
    from_             text,
    to_               text,
    value_              text,
    gas_price         text,
    gas                text,
    input_              text,
    creates            text,
    public_key        text,
    raw                text,
    r                  text,
    s                  text,
    v                  bigint,
    id                 bigint not null
        constraint transaction_pk
            primary key,
    block_number       bigint not null
        constraint transaction_block_number_fk
            references block
            on update cascade on delete cascade
);

alter table transaction
    owner to postgres;

create unique index transaction_hash_uindex
    on transaction (hash);

create unique index transaction_id_uindex
    on transaction (id);

create table log
(
    address            text,
    block_hash        text not null,
    block_number      text,
    data               text,
    log_index         text,
    transaction_hash  text,
    transaction_index text,
    id                 bigserial
        constraint log_pk
            primary key
);

alter table log
    owner to postgres;

create unique index log_id_uindex
    on log (id);

