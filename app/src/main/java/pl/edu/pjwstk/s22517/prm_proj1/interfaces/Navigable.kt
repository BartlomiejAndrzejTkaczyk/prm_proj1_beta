package pl.edu.pjwstk.s22517.prm_proj1.interfaces

interface Navigable {
    enum class Destination {
        List, Add, Edit
    }
    fun navigate(to: Destination)
}