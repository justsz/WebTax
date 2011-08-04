package webtax

class TagsTagLib {
	
	def redirectMainPage = {
		response.sendRedirect("${request.contextPath}/motu/list")
	  }
	
	def navigationBar = { attrs, body ->		
		def dataset = attrs.dataset
		
		out << "<div class='nav'>"
		
		out << "<span class='menuButton'>"
		out << link(class:'home', controller:'show', action:'list', params: [dataset: dataset]) {'List'}
		out << "</span>"
		
		out << "<span class='menuButton'>"
		out << link(class:'create', controller:'input', action:'add', params: [dataset: dataset]) {'Add MOTUs'}
		out << "</span>"
		
		out << "<span class='menuButton'>"
		out << link(class:'search', controller:'show', action:'search', params: [dataset: dataset]) {'Search'}
		out << "</span>"
		
		out << "<span class='menuButton'>"
		out << link(class:'represent', controller:'show', action:'repForm', params: [dataset: dataset]) {'Represent'}
		out << "</span>"		
		
		out << "</div>"					
	}
	
	
}