CREATE TABLE public.order_rules (
    id uuid PRIMARY KEY,
    name character varying(100) NOT NULL,
    group_id uuid,
    rule_offset integer
--    Unique on (group_id, name)
);

CREATE TABLE public.order_rule_items (
    order_rule_id uuid REFERENCES order_rules(id),
    order_id integer,
    user_id uuid,
    out_of_order_occurrences integer,
    index_id integer,
    PRIMARY KEY(order_rule_id, order_id)
);