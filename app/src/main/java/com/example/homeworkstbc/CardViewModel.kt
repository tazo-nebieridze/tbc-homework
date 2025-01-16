import androidx.lifecycle.ViewModel
import com.example.homeworkstbc.Input
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

typealias InputsList = List<List<Input>>

class CardViewModel : ViewModel() {

    val inputsData: InputsList
    val fieldValues: MutableMap<Int, String> = mutableMapOf()

    init {
        val jsonData = """
            [[{"field_id":1,"hint":"UserName","field_type":"input","keyboard":"text","required":false,"is_active":true,"icon":"https://jemala.png"},
              {"field_id":2,"hint":"Email","field_type":"input","required":true,"keyboard":"text","is_active":true,"icon":"https://jemala.png"},
              {"field_id":3,"hint":"phone","field_type":"input","required":true,"keyboard":"number","is_active":true,"icon":"https://jemala.png"}],
             [{"field_id":4,"hint":"FullName","field_type":"input","keyboard":"text","required":true,"is_active":true,"icon":"https://jemala.png"},
              {"field_id":14,"hint":"Jemali","field_type":"input","keyboard":"text","required":false,"is_active":true,"icon":"https://jemala.png"},
              {"field_id":89,"hint":"Birthday","field_type":"chooser","required":false,"is_active":true,"icon":"https://jemala.png"},
              {"field_id":898,"hint":"Gender","field_type":"chooser","required":false,"is_active":true,"icon":"https://jemala.png"}]]
        """.trimIndent()

        inputsData = Json.decodeFromString(jsonData)
    }
}
