package webtax

class Dataset {
	String name
	static hasMany = [motus: Motu]
	static fetchMode = [motus:"eager"]

    static constraints = {
		name(unique: true)
    }
}
