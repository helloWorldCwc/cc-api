/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: InitialState) {
  const { userInfo } = initialState ?? {};
  return {
    // 只要登录了就可以了
    canUser: userInfo !== undefined, 
    // 管理员才可以
    canAdmin: userInfo && userInfo.userRole === 'admin',
  };
}
