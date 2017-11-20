conf = new BaseConfiguration()
conf.setProperty("storage.directory", "/Users/rjurney/Software/open_business_graph/data")
conf.setProperty("storage.backend", "berkeleyje")
graph = TitanFactory.open(conf)

// Setup our graph schema
mgmt = graph.openManagement()

// Vertex labels
company = mgmt.makeVertexLabel('company').make()

// Node properties
domain = mgmt.makePropertyKey('domain').dataType(String.class).cardinality(Cardinality.SINGLE).make()

// Indexes
mgmt.buildIndex('byDomainUnique', Vertex.class).addKey(domain).unique().buildCompositeIndex()

// Relationships
partner = mgmt.makeEdgeLabel('partnership').multiplicity(MULTI).make()
customer = mgmt.makeEdgeLabel('customer').multiplicity(MULTI).make()
competitor = mgmt.makeEdgeLabel('competitor').multiplicity(MULTI).make()
follower = mgmt.makeEdgeLabel('follower').multiplicity(MULTI).make()
investor = mgmt.makeEdgeLabel('investor').multiplicity(MULTI).make()

// Commit changes
mgmt.commit()
