select *
from
    (select users.*, rownum ROWNR
     from users, users_roles, roles
     where users.id=users_roles.users_id
       and roles.id=users_roles.roles_id
       and users.is_delete =0
       and roles_id = 3