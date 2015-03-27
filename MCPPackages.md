# Introduction #
MCP is a well defined out of bounds messaging protocol that allows clients and servers to exchange information _behind the scenes_ without it impinginging on the visual experience of the main MOO interaction.

The client and server agree at connection time which MCP packages (if any) they will use.

# Details #
The MCP [IOPlugin](IOPlugin.md) takes care of negotiating with the server and initialising all of the packages that it knows about.

In the normal course of events, it examines incoming lines from the server and if they contain MCP data, it parses, identifies the appropriate package and passes the line to that package to do with as it pleases

## MCP Packages ##
### Standard Packages ###
A number of well known packages have been defined and are in use at a number of MOOs. the most complete list can be found at http://www.awns.com/mcp/

The client has implemented a number of these standard packages, some of them completely and some of them, less so:

**dns-org-mud-moo-simpleedit**. This allows editing (of verbs, properties and email) to be offloaded from the server to the client. Practically this means a graphical editor with such features as syntax highlighting, local backup of code to be supported.

We have implemented this package completely.

More details of the package can be found at http://www.awns.com/mcp/packages/README.dns-org-mud-moo-simpleedit

**dns-com-awns-visual**.

**dns-com-awns-serverinfo**.

**dns-com-awns-status**.

**dns-com-awns-timezone**.

If you feel a particular package should be implemented, please suggest it to the authors. Alternatively, write it yourself and send us the code for inclusion!

### Home Grown Packages ###
As well as the standard packages, the authors have created a package and produced a server side implementation to support it. It is:

**dns-uk-org-wookey-core**. This package takes the needs of MOO programmers to a new level, presenting much of the underlying detail of the MOO's object hierarchy in a tree, emphasing those areas that the user has declared a specific interest in. It presents much of the information about these objects, such as it's properties, it's verbs and it's place in the object hierarchy visually without all that nasty messing around in hyperspace!

The package protocol is still changing, but the latest definition can be found in the [WookeyCoreMCPPackage](WookeyCoreMCPPackage.md) page.

# Caveats #
If you are writing your own MCP package handlers, care should be taken to ensure that the package closes down cleanly when told to do so by the main MCP [IOPlugin](IOPlugin.md). It is not uncommon for package handlers to create threads and these need to be closed down cleanly.