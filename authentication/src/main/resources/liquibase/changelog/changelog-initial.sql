CREATE TABLE public.user (
    id uuid PRIMARY KEY,
    username character varying(40) NOT NULL,
    password character varying(40) NOT NULL,
    firstname character varying(40) NOT NULL,
    lastname character varying(40) NOT NULL,
    email character varying(255) NOT NULL,
    enabled BIT(1)
);