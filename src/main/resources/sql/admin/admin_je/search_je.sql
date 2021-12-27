select *
from
    (select users.*, rownum ROWNR
     from users, users_roles, roles
     where users.id=users_roles.users_id
       and roles.id=users_roles.roles_id
       and users.full_name like '%q%'
       and users.is_delete =0)
        tabWithRownum where tabWithRownum.ROWNR BETWEEN  :p_startrow and :p_endrow