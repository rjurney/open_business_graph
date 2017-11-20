import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

// Setup our database on top of berkeleydb (for now)
conf = new BaseConfiguration()
conf.setProperty("storage.directory", "/Users/rjurney/Software/open_business_graph/data")
conf.setProperty("storage.backend", "berkeleyje")
graph = TitanFactory.open(conf)

// Setup JSON Reading of MongoDB mongodump data
jsonSlurper = new JsonSlurper()

companies_filename = "/Users/rjurney/Software/marketing/jsondump/companies.json"
company_reader = new BufferedReader(new FileReader(companies_filename));

while((json = company_reader.readLine()) != null)
{
  document = jsonSlurper.parseText(json)

  println(document.domain)
  v = graph.addVertex('company')
  v.property("_id", document._id.$oid) 
  v.property("udpate_time", document.update_time.$date) 
  v.property("domain", document.domain)
  v.property("name", document.name)
}

// Get a graph traverser
g = graph.traversal()

// Create edges between companies
links_filename = "/Users/rjurney/Software/open_business_graph/links.json"
links_reader = new BufferedReader(new FileReader(links_filename));

update_time = new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))

while((json = links_reader.readLine()) != null)
{
  document = jsonSlurper.parseText(json)
  
  try {
    // Add edges to graph
    v1 = g.V().has('domain', document.home_domain).next()
    v2 = g.V().has('domain', document.link_domain).next()

    v1.addEdge(document.type, v2, 'update_time', update_time)
  }
  catch(Exception ex) {
    print("Error: " + ex + "\n") 
    print(document)
    print("\n")
    print(v1.values())
    print("\n")
    print(v2.values())
    print("\n")   
  }
}

