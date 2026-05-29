package dj.song.mixer.demoapp.model

data class UserDTO(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val company: Company
)

// to convert all field to required fields
fun UserDTO.toDomainUser(): DomainUser {
    return DomainUser(
        id = this.id,
        name = this.name,
        email = this.email,
        city = this.address.city // Extracting the nested data here!
    )
}
