CREATE TABLE public.users (
    id uuid PRIMARY KEY,
    username character varying(40) UNIQUE NOT NULL,
    password character varying(100) NOT NULL,
    firstname character varying(40) NOT NULL,
    lastname character varying(40) NOT NULL,
    email character varying(255) NOT NULL,
    enabled boolean
);

CREATE TABLE public.groups (
    id uuid PRIMARY KEY,
    name character varying(40) UNIQUE NOT NULL
);

CREATE TABLE public.users_groups (
    user_id uuid REFERENCES users(id),
    group_id uuid REFERENCES groups(id),
    admin boolean,
    PRIMARY KEY (user_id, group_id)
);