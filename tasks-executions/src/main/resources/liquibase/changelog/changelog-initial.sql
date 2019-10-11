CREATE TABLE public.house_tasks (
    id uuid PRIMARY KEY,
    rule_id uuid,
    user_id uuid,
    claimed_timestamp timestamp with time zone NOT NULL,
    checked_timestamp timestamp with time zone,
    status character varying(255) NOT NULL
);