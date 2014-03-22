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
            if (!Page.IsPostBack)
            {
                populateGridview();
                populateUserRoles();
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
        /// Populate userRoles
        /// </summary>
        private void populateUserRoles()
        {
            List<ManageUsers.UserRoles> dtRoles = ManageUsers.getUserRoles();
            ddlUserRoles.DataSource = dtRoles;
            ddlUserRoles.DataTextField = "Description";
            ddlUserRoles.DataValueField = "RoleId";
            ddlUserRoles.DataBind();
        }
      
        /// <summary>
        /// To add a new user in the database
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnSave_Click(object sender, EventArgs e)
        {
         
            ManageUsers.User newUser = new ManageUsers.User(txtUserName.Text, txtPassword.Text, Convert.ToInt32(ddlUserRoles.SelectedValue) ,
               txtFirstName.Text, txtLastName.Text, txtEmail.Text);
            ManageUsers.addUser(newUser);

            // go to the ManagerUsers page
            Response.Redirect("UsersManagement.aspx");
        }

        /// <summary>
        /// Depending on the user selection, the appropriate routine will run
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void gvUsers_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            int index = Convert.ToInt32(e.CommandArgument);
            if ( e.CommandName.Equals("deleteUser"))
            {
                string code = gvUsers.DataKeys[index].Value.ToString();
                hfCode.Value = code;
                System.Text.StringBuilder sb = new System.Text.StringBuilder();
                sb.Append(@"<script type='text/javascript'>");
                sb.Append("$('#deleteModal').modal('show');");
                sb.Append(@"</script>");
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "DeleteModalScript", sb.ToString(), false);
        
            }
        }

        protected void btnDelete_Click(object sender, EventArgs e)
        {
            //ManageUsers.deleteUser(8);
            // go to the ManagerUsers page
           // Response.Redirect("UsersManagement.aspx");

        }

        private void editUser()
        {
            ManageUsers.User newUser = new ManageUsers.User(txtUserName.Text, txtPassword.Text, Convert.ToInt32(ddlUserRoles.SelectedValue),
               txtFirstName.Text, txtLastName.Text, txtEmail.Text);
            ManageUsers.addUser(newUser);

            // go to the ManagerUsers page
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