using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;


namespace ParkingManagement.WebContent.UsersManagement
{
    public partial class UsersManagement : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // Ensure user has logged in!!!
            string key = ManageUsers.API_KEY;
            if (key == null || key.Length != 40)
                Response.Redirect(ResolveUrl("~/login.aspx"));

            if (!Page.IsPostBack)
            {
                populateGridview();
                    
             }
           
        }

        /// <summary>
        /// Populate the gridview with user information
        /// </summary>
        private void populateGridview()
        {
            List<ManageUsers.SuperUser> dtUsers = ManageUsers.getUsers();
            gvUsers.DataSource = dtUsers;
            gvUsers.DataBind();
            Cache["User"] = gvUsers.DataSource;
        }

        

        /// <summary>
        /// Depending on the user selection, the appropriate routine will run
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void gvUsers_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            int index = Convert.ToInt32(e.CommandArgument);
            if (index >= gvUsers.Rows.Count)
                return;

            string strUserId = gvUsers.DataKeys[index].Value.ToString();
            hfIndex.Value = index.ToString() ; 
            hfUserId.Value = strUserId;

            if ( e.CommandName.Equals("deleteUser"))
            {
                System.Text.StringBuilder sb = new System.Text.StringBuilder();
                sb.Append(@"<script type='text/javascript'>");
                sb.Append("$('#deleteModal').modal('show');");
                sb.Append(@"</script>");
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "DeleteModalScript", sb.ToString(), false);
        
            }
            else if ( e.CommandName.Equals("editUser"))
            {
                   getData(index);
                   Response.Redirect("EditUser.aspx");
                
            }
            else if (e.CommandName.Equals("resetPassword"))
            {
                getData(index);
                Response.Redirect("ResetPassword.aspx");
            }
        }


        /// <summary>
        /// Populate user form
        /// </summary>
        /// <param name="index"></param>
        private void getData( int index)
        {
            GridViewRow gvrow = gvUsers.Rows[index];
            
            Session["username"] = gvrow.Cells[0].Text;
            Session["lastname"] = gvrow.Cells[1].Text;
            Session["firstname"] = gvrow.Cells[2].Text;
            Session["email"] = gvrow.Cells[3].Text;
            Session["roleId"] = gvrow.Cells[5].Text;
            Session["isAdmin"] = gvrow.Cells[6].Text;
            Session["userId"] = hfUserId.Value;
        }

        
        protected void btnDelete_Click(object sender, EventArgs e)
        {
           ManageUsers.deleteUser(Convert.ToInt32(hfUserId.Value));
           Response.Redirect("UsersManagement.aspx");

        }

      
        protected void gvUsers_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvUsers.PageIndex = e.NewPageIndex;
            PageChangeData(); 

        }

        protected void PageChangeData()
        {
            List<ManageUsers.SuperUser> dtUsers = (List<ManageUsers.SuperUser>)Cache["User"];
            gvUsers.DataSource = dtUsers;
            gvUsers.DataBind();

        }

    

                
       
    }
}