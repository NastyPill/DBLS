create table block
(
    number             bigint not null
        constraint block_pk
            primary key,
    hash               text   not null,
    parent_hash        text,
    nonce              text,
    "sha3Uncles"       text,
    "logsBloom"        text,
    "transactionsRoot" text,
    "stateRoot"        text,
    "receiptsRoot"     text,
    author             text,
    miner              text,
    "mixHash"          text,
    difficulty         text,
    "totalDifficulty"  text,
    "extraData"        text,
    size               text,
    "gasLimit"         text,
    "gasUsed"          text,
    timestamp          timestamp
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
    "transactionIndex" text,
    "from"             text,
    "to"               text,
    value              text,
    "gasPrice"         text,
    gas                text,
    input              text,
    creates            text,
    "publicKey"        text,
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
    "blockHash"        text not null,
    "blockNumber"      text,
    data               text,
    "logIndex"         text,
    "transactionHash"  text,
    "transactionIndex" text,
    id                 bigserial
        constraint log_pk
            primary key
);

alter table log
    owner to postgres;

create unique index log_id_uindex
    on log (id);

