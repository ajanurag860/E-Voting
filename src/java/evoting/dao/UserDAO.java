package evoting.dao;
import evoting.dto.UserDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import evoting.dbutil.DBConnection;
public class UserDAO {
    private static PreparedStatement ps;
    static
    {
        try {           
            ps=DBConnection.getConnection().prepareStatement("Select user_type from user_details where adhar_no=? and password=?");
        }
      catch(SQLException ex)
        {
        System.out.println("Error In DB comm:"+ex);
        
        }
    }
public static String validateUser(UserDTO user) throws SQLException {
        ps.setString(1,user.getUserid());
        ps.setString(2,user.getPassword());
        ResultSet rs=ps.executeQuery();
        if(rs.next())
            return rs.getString(1);
      return null;
    }
}
