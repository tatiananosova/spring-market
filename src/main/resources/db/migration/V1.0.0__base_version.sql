create table product (
    id bigserial primary key,
    title text not null,
    price float not null,
    image_link text
);

---

create table category (
     id bigserial primary key,
     name text not null,
     alias text not null,
     parent_id int,

     foreign key (parent_id) references category(id)
);

create index category_parent_id_idx on category(parent_id);

---

create table product_category (
    product_id bigint not null,
    category_id bigint not null,

    primary key (product_id, category_id),
    foreign key (product_id) references product(id),
    foreign key (category_id) references category(id)
)
