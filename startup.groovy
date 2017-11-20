import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

// Setup our database on top of berkeleydb (for now)
conf = new BaseConfiguration()
conf.setProperty("storage.directory", "/Users/rjurney/Software/open_business_graph/data")
conf.setProperty("storage.backend", "berkeleyje")
graph = TitanFactory.open(conf)

// Get a graph traverser
g = graph.traversal()
