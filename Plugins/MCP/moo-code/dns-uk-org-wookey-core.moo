@create "generic MCP package" named dns-uk-org-wookey-core:dns-uk-org-wookey-core
;__NEWOBJ__.("messages_in") = {{"getinfo", {}}, {"getobj", {"objnum"}}}
;__NEWOBJ__.("messages_out") = {{"info", {"maxobj", "playerobj"}}, {"obj", {"name", "objnum", "properties", "verbs", "parents"}}}

@verb __NEWOBJ__:"send_to*" this none this
@program __NEWOBJ__:send_to
if ($perm_utils:controls(caller_perms(), this))
  return pass(@args);
else
  raise(E_PERM);
endif
.

@verb __NEWOBJ__:"handle_getinfo" this none this
@program __NEWOBJ__:handle_getinfo
{session} = args;
this:send_info(session, max_object(), player);
.

@verb __NEWOBJ__:"handle_getobj" this none this
@program __NEWOBJ__:handle_getobj
"Usage:  :handle_getobj(session, objnum)";
"";
{session, objnum} = args;
if (caller != this)
  raise(E_PERM);
endif
set_task_perms(session.connection);
if (!player.programmer)
  "You must have a P-bit to use this package";
  return;
endif
o = toobj(objnum);
if (!valid(o))
  raise(E_INVARG);
endif
ancestors = {};
what = o;
while (valid(what = parent(what)))
  ancestors = setadd(ancestors, tostr(what));
endwhile
this:send_obj(session, o.name, tostr(o), properties(o), verbs(o), ancestors);
.
