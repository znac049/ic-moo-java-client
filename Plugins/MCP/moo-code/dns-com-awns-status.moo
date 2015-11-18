@create "generic MCP package" named dns-com-awns-status:dns-com-awns-status
;__NEWOBJ__.("messages_out") = {{"", {"text"}}}

@verb __NEWOBJ__:"status" this none this
@program __NEWOBJ__:status
{session, text} = args;
if ((caller == this) || $perm_utils:controls(caller_perms(), session.connection))
  return this:send_(session, text);
endif
.
