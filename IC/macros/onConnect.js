// Called automatically by IC when a connection is established to a world
host = server.getHost();

if (host === "moo.harpers-tale.com") {
  server.writeLine('wiz who');
}