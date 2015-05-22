@dump #239 with create
@create "generic MCP package" named dns-com-awns-status:dns-com-awns-status
;#239.("messages_out") = {{"", {"text"}}}
;#239.("key") = 0
;#239.("aliases") = {"dns-com-awns-status"}
;#239.("offered") = #-1
;#239.("object_size") = {521, 1447709632}

@verb #239:"status" this none this
@program #239:status
{session, text} = args;
if ((caller == this) || $perm_utils:controls(caller_perms(), session.connection))
  return this:send_(session, text);
endif
.
