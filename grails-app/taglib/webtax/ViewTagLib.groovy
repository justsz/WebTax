package webtax

class ViewTagLib {
	static namespace = 'viewLink'
	def thisView = {
		out << "<div id='linkToView'>"		
		out << "<a href='http://localhost:8080${request.forwardURI}'>"
		out << "Link to this page </a>"
		out << "</div>"
		
	}
}
